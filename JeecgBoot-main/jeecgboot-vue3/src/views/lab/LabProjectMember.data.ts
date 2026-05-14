import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '项目ID',
    align:"center",
    dataIndex: 'projectId'
   },
   {
    title: '成员用户ID',
    align:"center",
    dataIndex: 'userId_dictText'
   },
   {
    title: '成员姓名',
    align:"center",
    dataIndex: 'username'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '项目ID',
    field: 'projectId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入项目ID!'},
          ];
     },
  },
  {
    label: '成员用户ID',
    field: 'userId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_user,username,id"
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入成员用户ID!'},
          ];
     },
  },
  {
    label: '成员姓名',
    field: 'username',
    component: 'Input',
    dynamicDisabled:true
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  projectId: {title: '项目ID',order: 0,view: 'text', type: 'string',},
  userId: {title: '成员用户ID',order: 1,view: 'sel_search', type: 'string',dictTable: "lab_user", dictCode: 'id', dictText: 'username',},
  username: {title: '成员姓名',order: 2,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}